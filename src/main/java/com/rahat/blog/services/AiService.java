package com.rahat.blog.services;

import com.rahat.blog.domain.entities.Category;
import com.rahat.blog.domain.entities.Tag;
import com.rahat.blog.mappers.CategoryMapper;
import com.rahat.blog.mappers.TagMapper;
import com.rahat.blog.repositories.CategoryRepository;
import com.rahat.blog.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class AiService {

    private final AiProviderService aiProviderService;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    AiService(AiProviderService aiProviderService, TagRepository tagRepository,CategoryRepository categoryRepository){
        this.aiProviderService = aiProviderService;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    public String generateBlog(String topic, UUID categoryId, Set<UUID> tagIds) {

        List<Tag> tags = tagRepository.findAllById(tagIds);
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tags){
            tagNames.add(tag.getName());
        }

        String categoryName = categoryRepository.findById(categoryId)
                .map(Category::getName)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Category not found: " + categoryId
                ));

        String structuredPrompt = """
        You are a senior technical blog writer.

        Write a concise, professional blog post (maximum 200 words).

        Topic: %s

        Category Context: %s
        Tags to include naturally: %s (if no tag given, ignore)

        Requirements:
        - Write title and then category and tag names. Then actual content.
        - Use clear technical language.
        - Maintain structured flow (Introduction → Core Idea → Practical Insight → Conclusion).
        - Do not use emojis.
        - Do not repeat the title.
        - Make it SEO-friendly but not keyword-stuffed.
        - Keep it informative and precise.
        """.formatted(topic, categoryName, String.join(", ", tagNames));

        return aiProviderService.generate(structuredPrompt);
    }

}

//