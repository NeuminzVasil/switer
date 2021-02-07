<#import "parts/common.ftlh" as c>
<#import  "parts/login.ftlh" as l>
<@c.page>

    <div>
        <@l.logout />
    </div>

    <div><a href="/customer">customer list</a></div>

    <div>
        <form method="post">
            <input type="text" name="text" placeholder="Введите сообщение">
            <input type="text" name="tag" placeholder="tag">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit">Добавить</button>
        </form>
    </div>

    <div>
        Список сообщений
    </div>

    <div>
        <form method="get" action="/main">
            <input type="text" name="filter_param" value="${filter}">
            <button type="submit">Найти</button>
        </form>
    </div>

    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <i>${message.customer}</i>
        </div>
    <#else> No messages
    </#list>

</@c.page>
