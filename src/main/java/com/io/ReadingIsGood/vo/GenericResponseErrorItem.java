package com.io.ReadingIsGood.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GenericResponseErrorItem {
    /*
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public ErrorItem error2;


     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public ErrorDetails error;

    /*
    public  GenericResponseErrorItem(ErrorItem errorItem) {
        this.error2 = errorItem;
    }
     */

    public  GenericResponseErrorItem(ErrorDetails errorDetails) {
        this.error = errorDetails;
    }
}
