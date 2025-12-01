package com.timeshards.service.impl;

import com.timeshards.common.BusinessException;
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
        return tagMapper.selectAll();
    }

    @Override
    public List<Tag> getHotTags(int limit) {
        return tagMapper.selectHotTags(limit);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public Tag getTagBySlug(String slug) {
        return tagMapper.selectBySlug(slug);
    }

    @Override
    public Tag createTag(Tag tag) {
        // 查重
        if (tagMapper.selectByName(tag.getName()) != null) {
            throw new BusinessException("标签名称已存在");
        }
        if (tagMapper.selectBySlug(tag.getSlug()) != null) {
            throw new BusinessException("标签别名已存在");
        }
        tagMapper.insert(tag);
        return tag;
    }

    @Override
    public void updateTag(Tag tag) {
        tagMapper.updateById(tag);
    }

    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteById(id);
    }
}
