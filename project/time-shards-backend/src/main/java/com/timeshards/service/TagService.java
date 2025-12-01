package com.timeshards.service;

import com.timeshards.entity.Tag;
import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    List<Tag> getHotTags(int limit);
    Tag getTagById(Long id);
    Tag getTagBySlug(String slug);
    Tag createTag(Tag tag);
    void updateTag(Tag tag);
    void deleteTag(Long id);
}
