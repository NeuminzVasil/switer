<#import "parts/common.ftlh" as c>
<@c.page>

    <div>
        <a href="/customer">customer list</a>
    </div>

    <div>
        Edit profile
    </div>

    <form action="/customer" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <input type="text" hidden value="${customer.id}" name="id">
        <input type="text" value="${customer.login}" name="login">
        <input type="text" value="<#if customer.firstName??>${customer.firstName}<#else>Null</#if>" name="firstName">
        <input type="text" value="<#if customer.lastName??>${customer.lastName}<#else>Null</#if>" name="lastName">
        <#list customer.authorities as authority>
            <div> ${authority} </div>
        </#list>

        <button type="submit">Save</button>
    </form>

</@c.page>