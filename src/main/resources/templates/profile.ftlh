<#import "parts/common.ftlh" as c>

<@c.page>
    ${message?ifExists}
    <h5>customer: ${customer!'NotSet'}</h5>
    <h5>username: ${username!'NotSet'}</h5>

    <form method="post">

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input class="form-control" type="password" name="password" placeholder="password"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> eMail: </label>
            <div class="col-sm-6">
                <input class="form-control" type="email" name="email" placeholder="eMail@eMail.com" value="${email!''}"/>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <button class="btn btn-primary" type="submit">
            Save
        </button>

    </form>
</@c.page>