package com.vance.qualitycode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.easymock.EasyMock;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class WebRetrieverTest {

    @Test
    public void testWebRetriever() {
        WebRetriever sut = new WebRetriever();

        assertThat(sut, notNullValue());
    }

    @Test
    public void testRetrieve_SingleURI() throws IOException {
        final String expectedContent = "This is one set of content";
        WebRetriever sut = new WebRetriever() {
            @Override
            protected HttpResponse retrieveResponse(String URI) throws IOException {
                return createMockResponse(expectedContent);
            }
        };

        String content = sut.retrieve("http://www.example.com");

        assertThat(content, notNullValue());
        assertThat(content, is(expectedContent));
    }

    @Test
    public void testExtractContentFromResponse() throws IOException {
        String expectedContent = "This is another set of content";
        WebRetriever sut = new WebRetriever();

        String content = sut.extractContentFromResponse(createMockResponse(expectedContent));

        assertThat(content, is(expectedContent));
    }

    private HttpResponse createMockResponse(String expectedContent) throws IOException {
        final HttpResponse response = EasyMock.createMock(HttpResponse.class);
        HttpEntity entity = EasyMock.createMock(HttpEntity.class);
        EasyMock.expect(response.getEntity()).andReturn(entity);
        EasyMock.expect(entity.getContent()).andReturn(new ByteArrayInputStream(expectedContent.getBytes()));
        EasyMock.replay(response, entity);
        return response;
    }
}
