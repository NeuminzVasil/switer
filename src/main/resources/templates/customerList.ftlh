<#import "parts/common.ftlh" as c>
<@c.page>

    <div>List of customers</div>

    <#list customers as customer>
        <div>
            <b>${customer.id}</b>
            <span>${customer.login}</span>
            <span><#if customer.firstName??>${customer.firstName}<#else>Null</#if></span>
            <span><#if customer.lastName??>${customer.lastName}<#else>Null</#if></span>
            <span>${customer.active?c}</span>
            <#list customer.authorities as authority>
                <span> ${authority} </span> <#sep>,
            </#list>
            <a href="/customer/${customer.id}">edit</a>
        </div>
    <#else> no customer info
    </#list>

</@c.page>