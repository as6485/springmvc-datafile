<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<h1>Online book store</h1> 
   <form:form method="post" action="search">  
      	<table >  
         <tr>  
           <td><input type="text" id ="txt" name="searchString" ></td>
           <td><input type="submit" value="Search" /></td>  
         </tr>  
        </table>  
    </form:form> 
       
      
<table border="2" width="70%" cellpadding="2">  
<tr><th>Id</th><th>Title <form:form method="post" action="sort"> 
<input type="submit" value="sort" />
</form:form></th><th>Author</th><th>Edit</th><th>Delete</th></tr>  
   <c:forEach var="book" items="${list}">   
   <tr>  
   <td>${book.id}</td>  
   <td>${book.title}</td>
   <td>${book.author}</td>  
   <td><a href="editbook/${book.id}">Edit</a></td>  
   <td><a href="deletebook/${book.id}">Delete</a></td>  
   </tr>  
   </c:forEach>  
   </table>  
   <br/>  
   <a href="bookform">Add New Book</a>  
