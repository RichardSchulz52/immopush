# immopush
This project implements a Telegram-Bot that fetches information from real estate websites, recognizes new entries, and pushes them to a telegram chat. 
It is currently supporting the german websites ebay-kleinanzeigen, immowelt and immonet. The bot is protected from beeing used by unauthorized users. 
If you are hosting it yourself you can be sure no one floods your server with search requests. I am running my own instance. If you want to use it message me :)

## How to use it
First open a Chat with the Bot or add it to a group. Than you have certain commands for search requests and administration (if you own the Bot).
### search request commands
- /add url 
  - replace url with the url in your browser. Go to the website you want to search on and configure the search filter. All filter information will be parsed to the url in the browser header. Copy the url and add it to the Bot. Make sure you klick on search before copying. Otherwise you might not have the latest filter change in the url.
- /display
  - this shows your current search requests. Each url has an identification number.
- /delete number
  - replace number with the identification number of the request you want to delete. Use the display command to get the identification numbers.

### administration
If you realy want to run your own server message me and I will provide the description here. 

## How to setup the server
If you realy want to run your own server message me and I will provide the description here.
