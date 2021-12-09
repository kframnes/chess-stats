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

Reports are generated with two levels of break-down: (1) all games, all wins, all loses first; (2) vs elo, position eval, etc. second.

```
Overview
===========
Book Moves: 5
Even Eval: +/- 100
Suspicious: +/- 5%

Report Games: 123
Report Moves: 1312

Related Elo: +/- 50
Related Games: 302
Related Moves: 4235

All Games
===========
...
...
...
Position Eval			Moves		T1%		T2%		T3%
-----------------------------------------------------------------------------------------------
Even (+/- 100 CP)		368        	40.22 (-2.52)	58.97 (+1.60)	70.65 (+5.50)
...
...
...

Won Games
===========
...
...
...

Lost Games
===========
...
...
...
```
