USE [master]
GO

/* For security reasons the login is created disabled and with a random password. */
/****** Object:  Login [user]    Script Date: 09/06/2023 18:56:04 ******/
CREATE LOGIN [user] WITH PASSWORD=N'abc123.', DEFAULT_DATABASE=[projects],  CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO

ALTER LOGIN [user] DISABLE
GO

ALTER SERVER ROLE [sysadmin] ADD MEMBER [user]
GO

CREATE USER [user] FOR LOGIN [user];  

