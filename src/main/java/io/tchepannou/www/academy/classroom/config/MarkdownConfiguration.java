package io.tchepannou.www.academy.classroom.config;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarkdownConfiguration {
    @Bean
    public Parser markdownParser (){
        return Parser.builder(markdownOptions()).build();
    }

    @Bean
    public HtmlRenderer markdownHtmlRenderer(){
        return HtmlRenderer.builder(markdownOptions()).build();
    }

    @Bean
    public DataHolder markdownOptions(){
        return new MutableDataSet();
    }
}
