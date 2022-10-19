# Online-bank-Api

#### This is my final project from the Ironhack bootcamp. It's a RESTapi of a Online Bank Api done with spring using some dependencies as Java dev tools, Secutity, Jdbc, Jpa, Web, MySQL, lombock, validation and jackson. I didn't put many comments in my code, I hope that you can get all missing information here in the readme.

### Models
I've made three packages with different classes that represents:

- Package accounts: including an abstract parent class called Account with some common properties of all kind of accounts. Also there are a model for: Checking Account, Credit Card, Saving Account and Student Checking Account.
- Package users: including an abstract parent class called User and its three child class Admin, Account Holder and Admin. It includes a model of Role( used for security purpose).
- Package transaction: a simple model that saves the information of all transactions made for the AccountHolders and Third Party.

### Enum
I have an Enum to indicate the two status that can be applied to account: ACTIVE and FROZEN.

### Embedables
I've used two embedables: the Money class(given by the project) and the Address class. 

### Repositories
A package containing all the repositories of all model classes.

### Security
This package contains the CustomUserDetails and the SecurityConfiguration classes used for security(Login accounts).

### Service
This one contains all the services and interfaces relative to the models. In the AccountHolderService, AdminService and ThirdPartyUserService where are the methods with the logic needed to make functional the Api.

### Controllers
The controllers packages contains the different controllers used to connect the methods done in the services with an Url so we can interact with the Api using a browser(or other apps that can help us with that interaction).

### Test
There I have three classes where I tested the different methods if they work correctly and if they throw the expected errors. 

### Others

- A bankapi.sql with the schema, a pom.xml where are all the dependencies.

### Controllers Methods

- Account Holder Controller methods:
    - transfer money.
    - get Accounts.
    - add Primary Address.
    - add Mailing Address.
    - get Balance.

- Admin Controller methods:
    - modify balance.
    - change Status Account.
    - Create New User and New Account.
    - get All Users.
    - delete Account.
    - create New Account By User.

- Third Party User Controller methods:
    - charge Money

### Services Methods

- Account Holder Service methods:
    - transferMoneyByAccountType. This method get a big DTO that contains all the necessary info so the Account Holder can make the transference of money from an own account to another account.
    - getAccounts. This is used to display a list with all the accounts related to the Account Holder.
    - getBalance. This one is divided in sub-methods in order to apply the different monthly maintenance fees, interest rates and others.
    - addPrimaryAddress. The method is used to add a primary address to the user account.
    - addMailingAddress. The method is used to add a mail address to the user account.
    - checkFraud. This one is a sub-method used to detect fraud when someone is trying to make more than one transfer in less than a second.
    - checkStrangeAmount. The sub-method search in the transaction history to check if the amount of the transfer is more than 150% bigger than the historical max transfer.
    - checkUserName. Another sub-method used to check if the user is the owner of the account.