Page Title: 

#Object Definitions
===================================================================================================================

Email						id		Email
next						id		next
Passwd						xpath		//input[@id='Passwd' and @type='password']
signIn						id		signIn

NewMessage			xpath		//span[contains(text(),'New Message')]
messageText			xpath		//input[@name='messageText']
Selectapp			xpath		//md-select[@aria-label='Select app']
AppID				xpath		//span[text()='com.qait.myapplication']
onSendMessage		xpath		//button[@ng-click='controller.onSendMessage()']
controllerConfirm	xpath		//button[@ng-click='controller.confirm()']/span

notificationValue	xpath	//android.widget.TextView[@resource-id='android:id/text'][@text='${values}']


===================================================================================================================