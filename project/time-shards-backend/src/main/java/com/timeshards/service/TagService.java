package com.timeshards.service;

import com.timeshards.entity.Tag;
import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    Tag createTag(Tag tag);
    void deleteTag(Long id);
}
