from django.db import models
from django.contrib.auth.models import AbstractUser
from django.utils.translation import gettext_lazy as _


# Create your models here.
class User(AbstractUser):
    """Overriding the default Django User model to add more fields
    Args:
        AbstractUser (_type_): _description_
    """

    role = models.CharField(max_length=50, null=True)
    password = models.CharField(_("password"), max_length=128, null=True)


class Archive(models.Model):
    """Archive model
    Args:
        models (_type_): _description_
    """

    name = models.CharField(max_length=100, null=True)
    description = models.TextField(null=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


class Resource(models.Model):
    """Resource model
    Args:
        models (_type_): _description_
    """

    TYPE_CHOICES = (
        ("video", "Video"),
        ("article", "Article"),
        ("book", "Book"),
        ("research_paper", "Research Paper"),
    )

    DIFF_CHOICES = (
        ("beginner", "Beginner"),
        ("intermediate", "Intermediate"),
        ("advanced", "Advanced"),
    )
    name = models.CharField(max_length=100, null=True)
    description = models.TextField(null=True)
    archive = models.ForeignKey(Archive, on_delete=models.CASCADE)
    link = models.URLField(null=True)
    resource_type = models.CharField(max_length=20, choices=TYPE_CHOICES)
    extra_params = models.JSONField(null=True, blank=True)
    difficulty = models.CharField(max_length=20, choices=DIFF_CHOICES)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
