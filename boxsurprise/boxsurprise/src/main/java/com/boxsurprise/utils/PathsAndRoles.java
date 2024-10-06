package com.boxsurprise.utils;

import com.boxsurprise.enuns.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PathsAndRoles {

    private String path;
    private List<TipoUsuario> roles;

}
