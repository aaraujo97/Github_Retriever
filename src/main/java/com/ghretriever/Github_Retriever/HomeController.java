package com.ghretriever.Github_Retriever;

import com.ghretriever.Github_Retriever.Entities.Application;
import com.ghretriever.Github_Retriever.Entities.Issue;
import com.ghretriever.Github_Retriever.Services.FormatterService;
import com.ghretriever.Github_Retriever.Services.RetrieverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private RetrieverService retriever;

    @Autowired
    private FormatterService formatterService;

    @GetMapping("/")
    public String home(Model model)
    {

        model.addAttribute("currentPage","home");
        List<Application> apps = new ArrayList<>();
        for (int i = 0; i < 3;++i)
        {
            apps.add(new Application());
        }
        apps.get(0).setName("LIFTR");
        apps.get(0).setTechnology("Ionic-Capacitor/Angular");
        apps.get(0).setPlatform("Mobile");

        apps.get(1).setName("Github_Retriever");
        apps.get(0).setTechnology("Java 17/ Springboot");
        apps.get(0).setPlatform("Localhost/Tomcat Server");

        apps.get(2).setName("distributed_project_2022");

        model.addAttribute("apps",apps);

        return "index";
    }

    @GetMapping("/getDependabotScans")
    public String getScans()
    {
        return "scans";
    }

    @GetMapping("/getIssues")
    public String getIssues(Model model)
    {
        List<Issue> issues = retriever.getIssues("LIFTR");

        List<String> htmlContent = formatterService.convertMarkdownToHtml(
                issues.stream()
                        .map(Issue::getBody)
                        .collect(Collectors.toList())
        );

        model.addAttribute("issues",issues);
        model.addAttribute("currentPage","applications");
        model.addAttribute("renderedIssueBodies", htmlContent);

        return "issues";
    }
}
