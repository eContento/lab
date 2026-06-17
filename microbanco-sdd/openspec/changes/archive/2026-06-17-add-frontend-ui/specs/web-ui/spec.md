## ADDED Requirements

### Requirement: Account List View
The system SHALL display a list of all accounts with their balances and status. The user SHALL be able to create new accounts from this view.

#### Scenario: View account list
- **WHEN** the user navigates to the account list
- **THEN** the system displays a table/cards with IBAN, owner name, currency, balance, and status for each account
- **AND** accounts with ACTIVE status are visually distinguished from CLOSED accounts

#### Scenario: Create a new account
- **WHEN** the user clicks "New Account" and fills in owner name, currency, and optional initial balance
- **THEN** the system sends POST /accounts with the data
- **AND** on success, the new account appears in the list
- **AND** on validation error, the system shows the error message from the API

#### Scenario: Paginated list
- **WHEN** there are more accounts than the page size
- **THEN** the system displays pagination controls (previous/next)

### Requirement: Account Detail View
The system SHALL display the details of a single account: balance, status, and transfer history. The user SHALL be able to execute transfers from this view.

#### Scenario: View account detail
- **WHEN** the user clicks on an account from the list
- **THEN** the system navigates to the account detail view showing IBAN, owner name, currency, balance, status, and creation date

#### Scenario: View transfer history
- **WHEN** the user is on the account detail view
- **THEN** the system displays a paginated list of transfers (both incoming and outgoing) with amount, description, source IBAN, target IBAN, and timestamp

#### Scenario: Execute a transfer
- **WHEN** the user enters source account (pre-filled from current account), target account IBAN, amount, and optional description
- **THEN** the system sends POST /transfers with the data
- **AND** on success, the balance updates and the transfer appears in history
- **AND** on error (insufficient balance, validation), the system shows the error message

#### Scenario: Close an account
- **WHEN** the user clicks "Close Account" on an ACTIVE account with zero balance
- **THEN** the system sends DELETE /accounts/{iban}
- **AND** the account status changes to CLOSED

#### Scenario: Close account with balance shows error
- **WHEN** the user clicks "Close Account" on an account with non-zero balance
- **THEN** the system shows an error: the account must be emptied first

### Requirement: Navigation
The system SHALL provide navigation between views without page reload.

#### Scenario: Navigate between views
- **WHEN** the user clicks the app title/logo
- **THEN** the system navigates to the account list view
- **WHEN** the user clicks on an account
- **THEN** the system navigates to the account detail view
- **WHEN** the user clicks a back button on the detail view
- **THEN** the system navigates back to the account list view

#### Scenario: Direct URL access
- **WHEN** the user enters a URL with a hash fragment (e.g., #/accounts/ES...)
- **THEN** the system loads the corresponding view directly

### Requirement: Visual Design
The UI SHALL use a sober, elegant design with soft colors.

#### Scenario: Color palette
- **WHEN** the UI is rendered
- **THEN** the background SHALL be a light gray (#f5f6fa)
- **AND** cards SHALL be white with subtle shadows
- **AND** the primary accent color SHALL be a soft blue (#4a6fa5)
- **AND** text SHALL be dark gray, not pure black

#### Scenario: Layout
- **WHEN** the UI is rendered
- **THEN** content SHALL be centered with a max-width container (1200px)
- **AND** cards SHALL have rounded corners (8px) and generous padding
- **AND** the navigation bar SHALL be fixed at the top with a subtle bottom border

### Requirement: Error Handling
The system SHALL display API errors in a user-friendly way.

#### Scenario: API error notification
- **WHEN** an API call returns a 4xx or 5xx error
- **THEN** the system displays a toast notification with the error message
- **AND** the notification auto-dismisses after 5 seconds
