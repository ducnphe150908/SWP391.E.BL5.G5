<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Apartment Management Dashboard</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            width: 80%;
            margin: auto;
            padding: 20px;
        }
        .header {
            text-align: center;
            padding: 10px 0;
        }
        .metric {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 20px;
            text-align: center;
            margin: 10px;
            float: left;
            width: 22%;
        }
        .clear {
            clear: both;
        }
        .announcements {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 20px;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Apartment Management Dashboard</h1>
        </div>

        <div class="metric">
            <h2>Available Apartments</h2>
            <p id="available-apartments"><%= request.getAttribute("availableApartments") %></p>
        </div>
        
        <div class="metric">
            <h2>Pending Maintenance Requests</h2>
            <p id="pending-maintenance"><%= request.getAttribute("pendingMaintenance") %></p>
        </div>
        
        <div class="metric">
            <h2>Rent Due Dates</h2>
            <p id="rent-due-dates"><%= request.getAttribute("rentDueDates") %></p>
        </div>
        
        <div class="metric">
            <h2>Recent Announcements</h2>
            <p id="recent-announcements"><%= request.getAttribute("recentAnnouncements") %></p>
        </div>
        
        <div class="clear"></div>
        
        <div class="announcements">
            <h2>Announcements</h2>
            <ul id="announcement-list">
                <%
                    List<String> announcements = (List<String>) request.getAttribute("announcements");
                    if (announcements != null) {
                        for (String announcement : announcements) {
                            out.println("<li>" + announcement + "</li>");
                        }
                    } else {
                        out.println("<li>No announcements</li>");
                    }
                %>
            </ul>
        </div>
    </div>
</body>
</html>
