package responses

type ErrorDetails struct {
	Message  string `json:"message"`
	Path     string `json:"path"`
	Code     int    `json:"code"`
	DateTime string `json:"dateTime"`
}

type SignUpResponse struct {
	Id                    int           `json:"id"`
	Uuid                  string        `json:"uuid"`
	Login                 string        `json:"login"`
	Password              string        `json:"password"`
	Name                  string        `json:"name"`
	LastName              string        `json:"lastName"`
	Patronymic            string        `json:"patronymic"`
	Email                 interface{}   `json:"email"`
	Roles                 []interface{} `json:"roles"`
	Enabled               bool          `json:"enabled"`
	Username              string        `json:"username"`
	Authorities           interface{}   `json:"authorities"`
	AccountNonExpired     bool          `json:"accountNonExpired"`
	CredentialsNonExpired bool          `json:"credentialsNonExpired"`
	AccountNonLocked      bool          `json:"accountNonLocked"`
}
