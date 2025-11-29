Chevannese Ellis (2301109) - Group Leader, Role [Customer View, Log4j2, Hosting of Remote Database, Client/Server, ERD, UML]
Jonathan Blackwood (2306822) - Member, Role [Login, Clerk View, Client/Server]
Matthew Webster (2305616) - Member, Role [Driver View, Manager View, Client/Server]
Andie Spencer (1701311) - Member, Role [Vehicle Scheduling, UI Design, Client/Server]


Instructions on how to install Project

1. Open Eclipse IDE.

2. Go to File → Import.

3. In the dialog, -> Selct Maven -> Existing Maven Projects into Workspace, then click Next.

4. Click Select root directory and browse to the folder containing your project.

5. Eclipse will list the projects it finds. Click the folder (make sure its unzipped) I sent here to import.

6. Then click the green play button to run the code. This should be located below the "Project" menu.


OR 
Clone the repository on the website provided below and do steps 1-6
https://github.com/Chevannese/APGroupProject2025-2026


Login Credentials:

<trn> <firstName> <lastName> <password> <contactNum> <email>

Customer
{"123456789", "Chevannese", "Ellis", "naruto123", "876-840-3526", "CHEV@gmail.com"}


Clerk
{"200000001", "Alicia", "Graham", "clerk123", "8765551010", "alicia.graham@example.com"},
{"200000002", "Brian", "Thompson", "secure456", "8765552020", "brian.thompson@example.com"},
{"200000003", "Camille", "Reid", "pass789", "8765553030", "camille.reid@example.com"},
{"200000004", "Dwayne", "Morgan", "hello321", "8765554040", "dwayne.morgan@example.com"},
{"200000005", "Elena", "Smith", "key987", "8765555050", "elena.smith@example.com"},
{"200000006", "Frank", "Johnson", "lock654", "8765556060", "frank.johnson@example.com"},
{"200000007", "Georgia", "Williams", "admin111", "8765557070", "georgia.williams@example.com"}


Driver

{"300000001", "Leroy", "Anderson","drive123", "8765558080", "leroy.anderson@example.com", "Driver"},
{"300000002", "Tanya", "Brown", "fast456", "8765559090", "tanya.brown@example.com", "Driver"},
{"300000003", "Marlon", "Green", "truck789", "8765551111", "marlon.green@example.com", "Driver"},
{"300000004", "Kimberly", "Davis", "road321", "8765552222", "kimberly.davis@example.com", "Driver"},
{"300000005", "Orville", "Campbell", "gear987", "8765553333", "orville.campbell@example.com", "Driver"},
{"300000006", "Sasha", "Stewart","load654", "8765554444", "sasha.stewart@example.com", "Driver"},
{"300000007", "Jason", "Williams", "shacman", "8765555555", "jason.williams@example.com", "Driver"}

Manager
{"400000001", "Oniel", "Charles","chess123", "876-123-4567", "oniel.charles@example.com", "Manager"},
{"400000002", "Oral", "Robinson", "late123", "876-890-7654", "oral.robinson@example.com", "Manager"},
{"400000003", "Rorron", "Clarke", "admin123", "876-752-8931", "rorron.clarke@example.com", "Manager"},
{"400000004", "Arnett", "Campbell", "mrabc123", "876-772-8831", "arnett.Campbell@example.com", "Manager"},
{"400000005", "Christine", "Anuli", "strict123", "876-097-8657", "christine.anuli@example.com", "Manager"},
{"400000006", "Christopher", "Udeagha","load654", "876-434-7573", "Christopher.Udeagha@example.com", "Manager"},
{"400000007", "Shawn", "Storm", "music101", "876-339-2831", "shawn.storm@example.com", "Manager"}

Depending on how you import the project it may show that properties folder is missing in the build Path -> Libraries. 
Please remove the missing properties folder and the program should run smoothly.


The program's database is stored remotely. However, if in the event you are unable to run the server due to meeting "maximum connections" 
from the remote database, I have provided all of the sql statements in "Documents" under the 
filename of sql document. So just copy and paste the entire code in the local database.
Afterwards, go into hibernate.cfg.xml and replace the connection url with your local database.
