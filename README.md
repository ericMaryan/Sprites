# Sprites
An application that creates sprites. The application uses Hibernate and RMI.

The user can click anywhere on the GUI to create a sprite. The sprites are created in order ( RED -> BLUE -> GREEN)
and they bounce around the screen. When the user creates a sprite, the application utilizes RMI to call the necessary methods located on the server. The client then displays the sprites on the client's GUI application.

Each sprite that is created is also stored in a MySQL database using Hibernate. The sprite's ID, colour, size, and position is saved in the database. 
