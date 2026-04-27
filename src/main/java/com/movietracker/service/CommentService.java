package com.movietracker.service;

import com.movietracker.dto.request.CommentRequest;
import com.movietracker.dto.response.CommentResponse;
import com.movietracker.entity.Comment;
import com.movietracker.entity.Movie;
import com.movietracker.entity.User;
import com.movietracker.exception.ForbiddenOperationException;
import com.movietracker.exception.ResourceNotFoundException;
import com.movietracker.repository.CommentRepository;
import com.movietracker.security.SecurityUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MovieService movieService;
    private final SecurityUtils securityUtils;

    @Transactional
    public CommentResponse add(Long movieId, CommentRequest request) {
        User user = securityUtils.currentUser();
        Movie movie = movieService.getMovieEntity(movieId);

        Comment comment = Comment.builder().text(request.getText()).user(user).movie(movie).build();
        commentRepository.save(comment);
        return DtoMapper.toCommentResponse(comment);
    }

    @Transactional
    public CommentResponse update(Long commentId, CommentRequest request) {
        User user = securityUtils.currentUser();
        Comment comment =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ForbiddenOperationException("You can only edit your own comments");
        }

        comment.setText(request.getText());
        commentRepository.save(comment);
        return DtoMapper.toCommentResponse(comment);
    }

    @Transactional
    public void delete(Long commentId) {
        User user = securityUtils.currentUser();
        Comment comment =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ForbiddenOperationException("You can only delete your own comments");
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> listForMovie(Long movieId) {
        Movie movie = movieService.getMovieEntity(movieId);
        return commentRepository.findByMovieOrderByCreatedAtAsc(movie).stream()
                .map(DtoMapper::toCommentResponse)
                .toList();
    }
}
