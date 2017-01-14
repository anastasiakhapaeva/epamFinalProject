package edu.training.web.command;

/**
 * Created by Roman on 25.11.2016.
 */
public enum CommandEnum {
    GO {
        {
            this.command = new ForwardCommand();
        }
    },
    CONFIRM_CLAIM{
        {
            this.command = new AdminConfirmCommand();
        }
    },
    DELETE_CLAIM {
        {
            this.command = new AdminDeleteCommand();
        }
    },
    DEPOSIT_MONEY {
        {
            this.command = new AjaxDepositMoneyCommand();
        }
    },
    SET_DISCOUNT_USER {
        {
            this.command = new AdminSetDiscountCommand();
        }
    },
    EDIT_HOSTEL {
        {
            this.command = new AdminEditHostelCommand();
        }
    },
    ADD_HOSTEL {
        {
            this.command = new AdminAddHostelCommand();
        }
    },
    BAN_USER {
        {
            this.command = new AdminBanUserCommand();
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
            this.command = new AjaxDeleteMessageCommand();
        }
    },
    AJAX_LOAD_NAMES {
        {
            this.command = new AjaxLoadNamesCommand();
        }
    },
    AJAX_LOAD_USER {
        {
            this.command = new AjaxLoadUserCommand();
        }
    },
    AJAX_FREE_PLACES {
        {
            this.command = new AjaxFreePlacesCommand();
        }
    },
    AJAX_LOAD_IMG {
        {
            this.command = new AjaxLoadImageCommand();
        }
    },
    AJAX_REGISTR {
        {
            this.command = new AjaxRegistrationCommand();
        }
    },
    AJAX_AUTHO {
        {
            this.command = new AjaxAuthorizationCommand();
        }
    },
    EMPTY {
        {
            this.command = new EmptyCommand();
        }
    },
    REGISTER {
        {
            this.command = new RegisterCommand();
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
