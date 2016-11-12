Feature: User able to received message when app is running at background  

Scenario: User received message notification when app is running at backGround and Open app received not notification
		Given I am at Application Home Page
		And Minimize it
		When I Login into the console
		And Send a message to device through console
		Then Verify message is received in notification window
		When open the Notification
		And Send a message to device through console
		Then No Notification received