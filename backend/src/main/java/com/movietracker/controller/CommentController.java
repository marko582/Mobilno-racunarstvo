package com.movietracker.controller;

import com.movietracker.dto.request.CommentRequest;
import com.movietracker.dto.response.CommentResponse;
import com.movietracker.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse add(
            @PathVariable Long movieId, @Valid @RequestBody CommentRequest request) {
        return commentService.add(movieId, request);
    }

    @PutMapping("/{commentId}")
    public CommentResponse update(
            @PathVariable Long commentId, @Valid @RequestBody CommentRequest request) {
        return commentService.update(commentId, request);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }

    @GetMapping("/movie/{movieId}")
    public List<CommentResponse> listForMovie(@PathVariable Long movieId) {
        return commentService.listForMovie(movieId);
    }
}
