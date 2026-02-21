package com.cricket.scorer.mapper;

import com.cricket.scorer.dto.MatchTeamDTO;
import com.cricket.scorer.model.MatchTeam;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MatchTeamMapper {

    MatchTeamDTO toDto(MatchTeam matchTeam);

    MatchTeam toEntity(MatchTeamDTO matchTeamDTO);
}
