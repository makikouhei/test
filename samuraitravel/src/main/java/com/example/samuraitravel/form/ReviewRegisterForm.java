package com.example.samuraitravel.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRegisterForm {
	@NotNull(message = "評価をを選択してください。")
    private Integer rating;   
    
   @NotBlank(message = "宿泊料金を入力してください。")
    private Integer price;  
}
