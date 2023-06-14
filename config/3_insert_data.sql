USE [projects]
GO

-- SET IDENTITY_INSERT to ON.  
SET IDENTITY_INSERT dbo.Project ON;  
GO  

INSERT INTO [dbo].[PROJECT]
           ([PROJNO],
		   [PROJNAME]
           ,[START_DATE])
     VALUES
           (1, 'Migración BD','2023-01-10'),
		   (2, 'Informa-T', '2022-10-01')
		   , (3, 'IA testing', '2021-01-20');
          
GO

-- SET IDENTITY_INSERT to OFF.  
SET IDENTITY_INSERT dbo.Project OFF;  
GO  

