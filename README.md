<p align="center"><a href=" "><img src="https://i.imgur.com/FliITzD.png" height="180px"></a></p>

Mindera_Events is a rest architecture API created with Spring Boot framework made for the Mindswap bootcamp at Mindera.<br>
It manages a mongoDB database of Mindera's Events, using the pattern model view controller, relating users to events and managing attendance and waiting lists. It also calls an exernal Api to generate a QR code for each Event.

 ## What it does:
 - Creates users with jwt authentication
 - Creates events with:
    - Title
    - Type of event
    - Approval status
    - Date
    - Starting time 
    - Attendance slots 
 - Allows for users to add themselves to event attendance lists
 - Sends email to users when registered
 - Logs all errors
 - Creates QRCode from an external API
<p></p>

 ## How it does it:
 
 - Adds user to event attendance list if there are available slots
 - Adds eventId to list of events on the user side
 - If there are no available slots, it adds the user to a waitting list
 <table><td><h6>
  if ((event.getAttendance().size() + 1) <= event.getSlots()){<br>
            event.getAttendance().add(user);<br>
            eventRepository.save(event);<br>
            user.getEvents().add(id);<br>
            userService.updateUser(userId,user);<br>
            return;<br>
  }<br>
  event.getWaitingList().add(user);<br>
        eventRepository.save(event);<br>
  }
 </h6></td></table>
 
 - Sends email with java email sender:
<table><td><h6>
        SimpleMailMessage message = new SimpleMailMessage();<br>
        message.setFrom("fromemail@gmail.com");<br>
        message.setTo(toEmail);<br>
        message.setText(body);<br>
        message.setSubject(subject);<br>
        mailSender.send(message);<br>
        System.out.println("Mail Sent...");<br>
    }<br>
</h6></td></table>

- Creates QRCode to send through email:

<table><td><h6>
HttpRequest request = HttpRequest.newBuilder()<br>
                .uri(URI.create("http://api.qrserver.com/v1/create-qr-code/?data="+userID+"&size=100x100"))<br>
                .method("GET", HttpRequest.BodyPublishers.noBody())<br>
                .build();<br>
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());<br>
        return response.body();<br>
 </h6></td></table>
    
## Contributors:

<table><tr>
  <td align="center"><a href="https://github.com/joaoguima24"><img src="https://avatars.githubusercontent.com/u/108727426?v=4" width="100px;" alt="João Guimarães"/><br /><sub><b>João Guimarães</b></sub></a><br /></td>   
  <td align="center"><a href="https://github.com/Iamcogita"><img src="https://avatars.githubusercontent.com/u/99983918?v=4" width="100px;" alt="David Mendes"/><br /><sub><b>David Mendes</b></sub></a><br /></td>   
  <td align="center"><a href="https://github.com/fabioiketani"><img src="https://avatars.githubusercontent.com/u/108727648?v=4" width="100px;" alt="Fabio Iketani"/><br /><sub><b>Fabio Iketani</b></sub></a><br /></td>    
  <td align="center"><a href="https://github.com/Interetion"><img src="https://avatars.githubusercontent.com/u/104978602?v=4" width="100px;" alt="Carla Pereira"/><br /><sub><b>Carla Pereira</b></sub></a><br /></td>
</tr></table>

<h3>THANK YOU FOR READING!</h3>
