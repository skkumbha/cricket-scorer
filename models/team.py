from sqlalchemy import Column, Integer, Text, TIMESTAMP, func
from sqlalchemy.orm import relationship
from .base import Base


class Team(Base):
    __tablename__ = 'teams'

    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(Text, nullable=False)
    short_name = Column(Text)
    logo_url = Column(Text)
    created_at = Column(TIMESTAMP, default=func.now())

    # Relationships
    team_players = relationship('TeamPlayer', back_populates='team', cascade='all, delete-orphan')
    players = relationship('Player', secondary='team_players', back_populates='teams', viewonly=True)
    match_teams = relationship('MatchTeam', back_populates='team')
    match_players = relationship('MatchPlayer', back_populates='team')
    batting_innings = relationship('Innings', foreign_keys='Innings.batting_team_id', back_populates='batting_team')
    bowling_innings = relationship('Innings', foreign_keys='Innings.bowling_team_id', back_populates='bowling_team')
