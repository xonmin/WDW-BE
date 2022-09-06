package com.wdw.wdw.dto.record;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecordAddResponseDto {

    private String username;

    private int quantity;

    @Builder
    public RecordAddResponseDto(String username, int quantity) {
        this.username = username;
        this.quantity = quantity;
    }

    public static RecordAddResponseDto from(User user, Record record) {
        return RecordAddResponseDto.builder()
                .username(user.getUsername())
                .quantity(record.getQuantity())
                .build();
    }

}
