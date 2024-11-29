package com.aerialarts.blog.service.validators;

import com.aerialarts.blog.entity.BlogLike;
import com.aerialarts.blog.exception.BadRequestException;
import com.aerialarts.blog.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeValidatorImpl implements LikeValidator {

    private final LikeRepository likeRepository;

    @Autowired
    public LikeValidatorImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public BlogLike findLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElseThrow(() -> new BadRequestException("Blog like with provided detail could not be found."));
    }
}
