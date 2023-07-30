package com.ghretriever.Github_Retriever.Services;

import com.ghretriever.Github_Retriever.Entities.Issue;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class FormatterService {

    public List<String> convertMarkdownToHtml(List<String> markdown)
    {
        List<String> html = new ArrayList<>();

        for (int i = 0; i < markdown.size();i++)
        {
            Parser parser = Parser.builder().build();
            Document document = parser.parse(markdown.get(i));
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            html.add(renderer.render(document));
        }

        return html;

    }
}
