package dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {
    private String orderId;

    public OrderDto(String orderId, String date, String custId) {
        this.orderId = orderId;
        this.date = date;
        this.custId = custId;
    }

    private String date;
    private String custId;
    private List<OrderDetailDto> list;
}
