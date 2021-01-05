CREATE DATABASE Checker
USE Checker
GO
CREATE TABLE Users(
	UserID INT IDENTITY(1,1),
	Name VARCHAR(30),
	Pass VARCHAR(30),
	Score INT DEFAULT 0,
	PRIMARY KEY (UserID)
);
INSERT INTO Users VALUES('DuongNeb','KongfuPanDa',10);

CREATE TABLE Room(
	RoomID INT IDENTITY(1,1),
	RMaster INT,
	UserID INT,
	PRIMARY KEY (RoomID),
	FOREIGN KEY (RMaster) REFERENCES Users(UserID),
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
)

select * from Room
DELETE Room WHERE RoomID > 0
select  * from Users
update Users set Statuss = 1

 ALTER TABLE Users ADD Statuss BIT
