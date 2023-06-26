from django.contrib.auth import get_user_model
from django.contrib.auth.models import Group, AnonymousUser
from .firebase_init import verify_user_token


class FirebaseTokenMiddleware:
    def __init__(self, get_response):
        self.get_response = get_response

    def __call__(self, request):
        try:
            token = request.META.get("HTTP_AUTHORIZATION", "").split(" ")[1]
            uid, role = verify_user_token(token)
            User = get_user_model()
            user, created = User.objects.get_or_create(username=uid)

            if created:
                group = Group.objects.get(name=role)
                user.is_staff = True if role in ["admin", "superadmin"] else False
                user.groups.add(group)
                user.save()

            request.user = user
        except Exception as e:
            print("Error occurred:", e)
            pass

        response = self.get_response(request)
        return response
