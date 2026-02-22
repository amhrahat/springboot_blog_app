package com.rahat.blog.services.impl;

import com.rahat.blog.domain.commands.CreateTagCommand;
import com.rahat.blog.domain.dtos.TagDto;
import com.rahat.blog.domain.entities.Tag;
import com.rahat.blog.domain.entities.User;
import com.rahat.blog.mappers.TagMapper;
import com.rahat.blog.repositories.TagRepository;
import com.rahat.blog.security.SendOTP;
import com.rahat.blog.services.CurrentUserService;
import com.rahat.blog.services.TagService;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final CurrentUserService currentUserService;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper,
                          CurrentUserService currentUserService) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.currentUserService = currentUserService;
    }


    @Override
    public TagDto createTag(CreateTagCommand createTagCommand) {
        User currentUser = currentUserService.getCurrentUser();

        Tag tag = new Tag(
                null,
                createTagCommand.name(),
                currentUser
        );

        Tag saved = tagRepository.save(tag);
        currentUser.setTags(List.of(tag));
        return tagMapper.toDto(saved);
    }



    @Override
    public List<TagDto> tagLists() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDto> dtos = new ArrayList<>();

        for (Tag tag : tags){
          TagDto dto = tagMapper.toDto(tag);
                        dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void deleteTag(UUID tagId) {

        User currentUser = currentUserService.getCurrentUser();

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() ->
                        new RuntimeException("Tag not found"));
        if (!tag.getAuthor().getId()
                .equals(currentUser.getId())) {


            throw new AccessDeniedException(
                    "You can delete only your own tags"
            );
        }

        tagRepository.delete(tag);
    }

}

