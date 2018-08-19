/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.api.model.outbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.video.rental.store.domain.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author spykee
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class RentReturnOutboundDto extends RentOutboundDto {

    private Price totalPriceCharge;


}
