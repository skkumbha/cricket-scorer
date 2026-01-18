from sqlalchemy import Column, Integer, Text, TIMESTAMP, func
from sqlalchemy.orm import relationship
from .base import Base


class Player(Base):
    __tablename__ = 'players'

    id = Column(Integer, primary_key=True, autoincrement=True)
    full_name = Column(Text, nullable=False)
    jersey_number = Column(Integer)
    role = Column(Text)
    created_at = Column(TIMESTAMP, default=func.now())

    # Relationships
    team_players = relationship('TeamPlayer', back_populates='player', cascade='all, delete-orphan')
    teams = relationship('Team', secondary='team_players', back_populates='players', viewonly=True)
    match_players = relationship('MatchPlayer', back_populates='player')
    overs = relationship('Over', back_populates='bowler')
    striker_balls = relationship('Ball', foreign_keys='Ball.striker_id', back_populates='striker')
    non_striker_balls = relationship('Ball', foreign_keys='Ball.non_striker_id', back_populates='non_striker')
    bowler_balls = relationship('Ball', foreign_keys='Ball.bowler_id', back_populates='bowler')
    dismissed_balls = relationship('Ball', foreign_keys='Ball.dismissed_player_id', back_populates='dismissed_player')
    match_awards = relationship('MatchAward', back_populates='player')
