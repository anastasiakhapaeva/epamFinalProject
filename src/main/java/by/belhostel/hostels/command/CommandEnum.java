package by.belhostel.hostels.command;

import by.belhostel.hostels.command.admin.*;
import by.belhostel.hostels.command.site.*;
import by.belhostel.hostels.command.user.*;

/**
 * Created by Roman on 25.11.2016.
 */
public enum CommandEnum {
    GO {
        {
            this.command = new ForwardCommand();
        }
    },
    CONFIRM_CLAIM {
        {
            this.command = new ConfirmCommand();
        }
    },
    DELETE_CLAIM {
        {
            this.command = new DeleteCommand();
        }
    },
    DEPOSIT_MONEY {
        {
            this.command = new DepositMoneyCommand();
        }
    },
    SET_DISCOUNT_USER {
        {
            this.command = new SetDiscountCommand();
        }
    },
    EDIT_HOSTEL {
        {
            this.command = new EditHostelCommand();
        }
    },
    SHOW_USERS {
        {
            this.command = new ShowUsersCommand();
        }
    },
    ADD_HOSTEL {
        {
            this.command = new AddHostelCommand();
        }
    },
    BAN_USER {
        {
            this.command = new BanUserCommand();
        }
    },
    BOOK_CANCEL {
        {
            this.command = new CancelBookingCommand();
        }
    },
    BOOK_HOSTEL {
        {
            this.command = new BookingCommand();
        }
    },
    MY_HOSTELS {
        {
            this.command = new ShowMyHostelsCommand();
        }
    },
    SHOW_HOSTEL {
        {
            this.command = new ShowHostelCommand();
        }
    },
    FIND_HOSTELS {
        {
            this.command = new FindHostelsCommand();
        }
    },
    AJAX_DELETE_MESSAGE {
        {
            this.command = new DeleteMessageCommand();
        }
    },
    AJAX_CHECK_STATE {
        {
            this.command = new BookingStateCommand();
        }
    },
    AJAX_LOAD_NAMES {
        {
            this.command = new LoadNamesCommand();
        }
    },
    AJAX_LOAD_USER {
        {
            this.command = new LoadUserCommand();
        }
    },
    AJAX_CHANGE_LANG {
        {
            this.command = new LanguageCommand();
        }
    },
    AJAX_FREE_PLACES {
        {
            this.command = new FreePlacesCommand();
        }
    },
    AJAX_LOAD_IMG {
        {
            this.command = new LoadImageCommand();
        }
    },
    AJAX_REGISTR {
        {
            this.command = new LoginExistenceCommand();
        }
    },
    AJAX_AUTHO {
        {
            this.command = new AuthorizationCommand();
        }
    },
    EMPTY {
        {
            this.command = new EmptyCommand();
        }
    },
    REGISTER {
        {
            this.command = new RegistrationCommand();
        }
    },
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }

    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }

}
