package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.security.SecurityUser;
import ru.skypro.homework.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * Метод для получения комментариев по ид объявления
     *
     * @param adId идентификатор объявления
     * @return дто-объект содержащий все комментарии объявления {@link ResponseWrapperComment}
     */
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable(name = "id") @Min(1L) Long adId) {
        return ResponseEntity.ok(commentService.getComments(adId));
    }

    /**
     * Метод для создания комментария
     *
     * @param adId        идентификатор объявления
     * @param comment     дто-объект, содержащий данные для создания комментария
     * @param currentUser данные о текущем пользователе в виде оберточного объекта {@link SecurityUser}
     * @return данные о созданном комментарии в виде дто-объекта {@link CommentDTO}
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComments(@PathVariable(name = "id") @Min(1L) Long adId,
                                                  @RequestBody @Valid CommentDTO comment,
                                                  @AuthenticationPrincipal SecurityUser currentUser) {
        return ResponseEntity.ok(commentService.addComments(adId, comment, currentUser));
    }

    /**
     * Метод для удаления комментария по его ид
     *
     * @param adId        идентификатор объявления
     * @param id          идентификатор комментария
     * @param currentUser данные о текущем пользователе в виде оберточного объекта {@link SecurityUser}
     * @return код 200 - если объект успешно удален или не существовал
     */
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComments(@PathVariable("adId") @Min(1L) Long adId,
                                            @PathVariable("commentId") @Min(1L) Long id,
                                            @AuthenticationPrincipal SecurityUser currentUser) {
        commentService.removeCommentById(adId, id, currentUser);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод для изменения комментария по его ид
     *
     * @param adId        идентификатор объявления
     * @param id          идентификатор комментария
     * @param comment     дто-объект содержащий данные о обновленном комментарии
     * @param currentUser данные о текущем пользователе в виде оберточного объекта {@link SecurityUser}
     * @return данные о обновленном комментарии в виде дто-объекта {@link CommentDTO}
     */
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComments(@PathVariable("adId") @Min(1L) Long adId,
                                                     @PathVariable("commentId") @Min(1L) Long id,
                                                     @RequestBody @Valid CommentDTO comment,
                                                     @AuthenticationPrincipal SecurityUser currentUser) {
        return ResponseEntity.ok(commentService.updateComment(id, comment, currentUser));
    }

}
