package com.timeshards.service.impl;

import com.timeshards.entity.Tag;
import com.timeshards.mapper.TagMapper;
import com.timeshards.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.findAll();
    }

    @Override
    public Tag createTag(Tag tag) {
        tagMapper.insert(tag);
        return tag;
    }

    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteById(id);
    }
}
