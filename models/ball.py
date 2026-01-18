from sqlalchemy import Column, Integer, Boolean, Text, ForeignKey
from sqlalchemy.orm import relationship
from .base import Base


class Ball(Base):
    __tablename__ = 'balls'

    id = Column(Integer, primary_key=True, autoincrement=True)
    over_id = Column(Integer, ForeignKey('overs.id', ondelete='CASCADE'))
    ball_number = Column(Integer)
    striker_id = Column(Integer, ForeignKey('players.id'))
    non_striker_id = Column(Integer, ForeignKey('players.id'))
    bowler_id = Column(Integer, ForeignKey('players.id'))
    runs_off_bat = Column(Integer, default=0)
    extras = Column(Integer, default=0)
    extra_type = Column(Text)
    is_wicket = Column(Boolean, default=False)
    wicket_type = Column(Text)
    dismissed_player_id = Column(Integer, ForeignKey('players.id'))

    # Relationships
    over = relationship('Over', back_populates='balls')
    striker = relationship('Player', foreign_keys=[striker_id], back_populates='striker_balls')
    non_striker = relationship('Player', foreign_keys=[non_striker_id], back_populates='non_striker_balls')
    bowler = relationship('Player', foreign_keys=[bowler_id], back_populates='bowler_balls')
    dismissed_player = relationship('Player', foreign_keys=[dismissed_player_id], back_populates='dismissed_balls')
