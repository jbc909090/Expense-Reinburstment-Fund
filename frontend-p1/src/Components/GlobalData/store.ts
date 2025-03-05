export const store = {
    loggedInUser:{
        userId:0,
        username:"",
        role:"",
        fname:"",
        lname:""
    }

    //NOT BEST PRACTICE BTW! For one, the data will be wiped if you refresh the browser
    //look into context API and local storage for a more modern take on this store

}