package com.ui.model.entity.registration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Appointment {
    private String appointmentTittle;
    private String dateTime;
}

