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
<<<<<<< HEAD
)
=======
)

select * from Room
DELETE Room WHERE RoomID > 0
select  * from Users
>>>>>>> 2ad7b2e0f6d34fc9d5ac3e12fed8f0a12f929a87
