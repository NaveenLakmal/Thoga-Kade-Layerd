package dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailTm {

    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
