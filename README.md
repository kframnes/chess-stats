# ChessStats
A system for importing (from pgn) and reporting on Chess move statistics.

# Modes

### Setup
Given a DB host construct schema necessary for the storage and reporting of statistics.

```
create table games
(
	id int auto_increment,
	site enum('OTB', 'Chess_com', 'Lichess_org') not null,
	type enum('Blitz', 'Rapid', 'Classical') not null,
	white_player varchar(50) not null,
	black_player varchar(50) not null,
	black_elo int null,
	white_elo int null,
  result enum('1-0', '0-1', '1/2-1/2') not null,
	constraint games_pk
		primary key (id)
);

create table moves
(
	id int auto_increment,
	game_id int not null,
	move_color enum('white', 'black') not null,
	move_number int not null,
	eval_before float not null comment 'position eval before this move',
	eval_after float not null comment 'position eval after this move',
	mating boolean not null 'was there a mate-in-N in the position',
  move_t int null comment 'what engine move was this; null if not recommended by engine',
  forced boolean not null 'was the move considered forcing; within engine recommendation, but only option',
	constraint moves_pk
		primary key (id)
);
```

### Import
Import moves and store statistics.

### Report
Query for relavent cross-section of data and display report.
