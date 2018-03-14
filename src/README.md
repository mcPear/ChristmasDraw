1. Running Angular-client<br />
go to src/client<br />
type npm start (or npm install before if not installed)<br />
url http://localhost:4200<br />
<br />
2. Running SpringBoot<br />
use Intellij project<br />
for port :8090 copy default_applicatioin.properties to applicatioin.properties<br />
<br />
3. running keycloak<br />
use own standalone version<br />
import file src/keycloak/realm-export.json to keycloak<br />
port :8080<br />
<br />
//todo dockerize

Tips<br />
Don't forget about credentials when setting up keycloack config