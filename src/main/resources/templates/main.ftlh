<#import "parts/common.ftlh" as c>
<@c.page>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter_param" class="form-control" value="${filter?ifExists}"
                       placeholder="поиск по tag">
                <button type="submit" class="btn btn-primary ml-2">Найти</button>
            </form>
        </div>
    </div>

    <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input class="form-control" type="text" name="text" placeholder="Введите сообщение">
                </div>
                <div class="form-group">
                    <input class="form-control" type="text" name="tag" placeholder="Введите таг">
                </div>
                <div class="form-group">
                    <div class="input-group mb-3">
                        <input type="file" class="form-control" id="customFile" name="file">
                        <label class="input-group-text" for="customFile">Upload</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card-columns">
        <#list messages as message>
            <div class="card my-3">
                <#if message.filename??>
                    <img class="card-img-top" src="/upload/${message.filename}" alt="no image">
                </#if>
                <span>${message.text}</span>
                <i>${message.tag}</i>
                <div class="card-footer text-muted">
                    ${message.customer}
                </div>
            </div>
        <#else> No messages
        </#list>
    </div>

</@c.page>
