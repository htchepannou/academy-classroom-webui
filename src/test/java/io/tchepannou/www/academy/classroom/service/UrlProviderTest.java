package io.tchepannou.www.academy.classroom.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UrlProviderTest {
    @InjectMocks
    UrlProvider provider;

    @Test
    public void getSegmentUrl() throws Exception {
        assertThat(provider.getSegmentUrl(100, null, null)).isEqualTo("/classroom/100");
        assertThat(provider.getSegmentUrl(100, 1, null)).isEqualTo("/classroom/100/1");
        assertThat(provider.getSegmentUrl(100, 1, 2)).isEqualTo("/classroom/100/1/2");
    }

}
